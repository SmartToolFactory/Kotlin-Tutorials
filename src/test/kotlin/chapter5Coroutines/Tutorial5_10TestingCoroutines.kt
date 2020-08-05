package chapter5Coroutines

import com.google.common.truth.Truth
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test


/**
 * Dispatchers.Main Delegation
 *
 * Dispatchers.setMain will override the Main dispatcher in test situations.
 * This is helpful when you want to execute a test in situations where the platform
 * Main dispatcher is not available, or you wish to replace Dispatchers.Main with a testing dispatcher.
 * * Once you have this dependency in the runtime, ServiceLoader mechanism will overwrite Dispatchers.Main with a testable implementation.
 *
 * @see  <a href="https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-test/">Coroutines Tests</a>
 *
 */
class DispatchersMainDelegationTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun testSomeUI() = runBlocking {
        println("Test started")
        launch(Dispatchers.Main) {  // Will be launched in the mainThreadSurrogate dispatcher
            // ...

            val result = getResultWithDelay()
            println("in launch() scope: $this, thread: ${Thread.currentThread().name}, result: $result")
        }

        println("Test is finished")
    }


    private suspend fun getResultWithDelay(): String {
        delay(3_000)
        return "Hello World"
    }

}


/**
 * # runBlockingTest
 * To test regular suspend functions or coroutines started with launch or async use the
 * * runBlockingTest coroutine builder that provides extra test control to coroutines.
 *
 * * Auto-advancing of time for regular suspend functions
 * * Explicit time control for testing multiple coroutines
 * * Eager execution of launch or async code blocks
 * * Pause, manually advance, and restart the execution of coroutines in a test
 * * Report uncaught exceptions as test failures
 */
class RunBlockingTests {

    /**
     * To test regular suspend functions, which may have a delay,
     * you can use the runBlockingTest builder to start a testing coroutine.
     * Any calls to delay will automatically advance virtual time by the amount delayed.
     */
    @Test
    fun testFoo() = runBlockingTest { // a coroutine with an extra test control
        val actual = foo()
        // ...
    }

    private suspend fun foo() {
        delay(1_000) // auto-advances virtual time by 1_000ms due to runBlockingTest
        // ...
    }


    @Test
    fun `Run blocking test using async`() = runBlockingTest {

        // GIVEN
        val expected = "Hello World"

        // WHEN
        val actual = async {
            // 🔥🔥 Time is advanced by delay duration instantly
            getResultWithVeryLongDelay()
        }

        // THEN
        Truth.assertThat(actual.await()).isEqualTo(expected)
    }


    /**
     * ❌ This test fails
     */
    @Test
    fun `Run blocking test using launch`() = runBlockingTest {

        // GIVEN
        val expected = "Hello World"
        var result = ""

        // WHEN
        launch {
            // 🔥🔥 Time is advanced by delay duration instantly
            result = getResultWithVeryLongDelay()
        }

        // THEN
        Truth.assertThat(result).isEqualTo(expected)
    }

    private suspend fun getResultWithVeryLongDelay(): String {
        delay(10_000)
        return "Hello World"
    }


    /**
     *  ## Testing launch or async
     *  Inside of runBlockingTest, both launch and async will start
     *  a new coroutine that may run concurrently with the test case.
     *
     *  To make common testing situations easier, by default the body of the coroutine
     *  is executed eagerly until the first call to delay or yield.
     *
     *  * runBlockingTest will auto-progress virtual time until
     *  all coroutines are completed before returning.
     *  If any coroutines are not able to complete, an UncompletedCoroutinesError will be thrown.
     *
     * ### Note: The default eager behavior of runBlockingTest will ignore CoroutineStart parameters.
     */
    @Test
    fun testFooWithLaunch() = runBlockingTest {
        foo()
        // the coroutine launched by foo() is completed before foo() returns
        // ...
        println("Test is finished")

        /*
            🔥 If bar() has no delay
            Prints:
            in foo()
            Test is finished

            🔥 If bar() has some delay it does not still not delay the test but
            execution order changes

            Prints:
            Test is finished
            bar executed in main @coroutine#2
            in foo()
         */
    }

    private fun CoroutineScope.foo() {
        // This coroutines `Job` is not shared with the test code
        launch {
            bar()      // executes eagerly when foo() is called due to runBlockingTest
            println("in foo()") // executes eagerly when foo() is called due to runBlockingTest
        }
    }

    private suspend fun bar() {
        delay(3000)
        println("bar executed in ${Thread.currentThread().name}")
    }


    /**
     * ## Testing launch or async with delay
     *
     * 🔥 NOT WORKING, advance time with launch is probably broken for now
     *
     * If the coroutine created by launch or async calls delay then the runBlockingTest
     * will not auto-progress time right away.
     * This allows tests to observe the interaction of multiple coroutines with different delays.

     * To control time in the test you can use the DelayController interface.
     * The block passed to runBlockingTest can call any method on the DelayController interface.
     *
     * ### Note: runBlockingTest will always attempt to auto-progress time until all coroutines are completed just before exiting.
     * ### This is a convenience to avoid having to call `advanceUntilIdle` s the last line of many common test cases.
     *  If any coroutines cannot complete by advancing time, an UncompletedCoroutinesError is thrown.
     */

    @Test
    fun testFunctionWithLaunchAndDelay() = runBlockingTest {
        someFunWithDelay()
        anotherFunWithDelay()
        // the coroutine launched by someFunWithDelay has not completed here, it is suspended waiting for delay(1_000)
        advanceTimeBy(1_000) // progress time, this will cause the delay to resume
        // the coroutine launched by someFunWithDelay has completed here
        // ...
    }

    private suspend fun CoroutineScope.someFunWithDelay() {
        launch {
            println("someFunWithDelay() Start fun")   // executes eagerly when someFunWithDelay() is called due to runBlockingTest
            delay(1_000) // suspends until time is advanced by at least 1_000
            println("someFunWithDelay() End fun")   // executes after advanceTimeBy(1_000)
        }
    }

    private suspend fun CoroutineScope.anotherFunWithDelay() {
        launch {
            println("anotherFunWithDelay() Start fun")   // executes eagerly when someFunWithDelay() is called due to runBlockingTest
            delay(2_000) // suspends until time is advanced by at least 2_000
            println("anotherFunWithDelay() End fun")   // executes after advanceTimeBy(2_000)
        }
    }


    /**
     * # 🔥🔥 ❌ NOT WORKING? TimeoutCancellationException is NOT thrown
     *
     * ## Testing withTimeout using runBlockingTest
     * Time control can be used to test timeout code.
     * To do so, ensure that the function under test is suspended inside a
     * `withTimeout` block and advance time until the timeout is triggered.
     *
     * Depending on the code, causing the code to suspend may need to
     * use different mocking or fake techniques.
     * For this example an uncompleted `Deferred<Foo>` is provided
     * to the function under test via parameter injection.
     */

    // TODO This code is not WORKING, and test FAILS because something is wrong with LAUNCH
    @Test(expected = TimeoutCancellationException::class)
    fun testFooWithTimeout() = runBlockingTest {

        val uncompleted = CompletableDeferred<Foo>() // this Deferred<Foo> will never complete
        fooWithTimeout(uncompleted)
        advanceTimeBy(1_000) // advance time, which will cause the timeout to throw an exception
        // ...
    }

    private suspend fun CoroutineScope.fooWithTimeout(resultDeferred: Deferred<Foo>) {

        launch {
            withTimeout(1_000) {
                resultDeferred.await() // await() will suspend forever waiting for uncompleted
                // ...
            }
        }
    }

    class Foo {}

    /**
     *
     * ## I don't have an idea what this does???
     *
     * ## Using pauseDispatcher for explicit execution of runBlockingTest
     *
     * The eager execution of launch and async bodies makes many tests easier,
     * but some tests need more fine grained control of coroutine execution.
     * To disable eager execution, you can call pauseDispatcher to pause
     * the TestCoroutineDispatcher that runBlockingTest uses.
     *
     * When the dispatcher is paused, all coroutines will be added to a queue instead running.
     * In addition, time will never auto-progress due to delay on a paused dispatcher.
     */

    @Test
    fun testFooWithPauseDispatcher() = runBlockingTest {

        pauseDispatcher {
            fooWithPauseDispatcher()
            // the coroutine started by foo has not run yet
            runCurrent() // the coroutine started by foo advances to delay(1_000)
            // the coroutine started by foo has called println(1), and is suspended on delay(1_000)
            advanceTimeBy(1_000) // progress time, this will cause the delay to resume
            // the coroutine started by foo has called println(2) and has completed here
        }
        // ...
    }

    private fun CoroutineScope.fooWithPauseDispatcher() {
        launch {
            println(1)   // executes after runCurrent() is called
            delay(1_000) // suspends until time is advanced by at least 1_000
            println(2)   // executes after advanceTimeBy(1_000)
        }
    }

}

/**
 *
 * # Integrating tests with structured concurrency
 *
 * Code that uses structured concurrency needs a CoroutineScope in order to launch a coroutine.
 * In order to integrate runBlockingTest with code that uses common structured concurrency
 * patterns tests can provide one (or both) of these classes to application code.
 */
class IntegratingWithStructuredConcurrencyTests {

    /**
     * ## Providing an explicit TestCoroutineScope
     *
     * * Both classes are provided to allow for various testing needs.
     * Depending on the code that’s being tested, it may be easier to provide
     * a TestCoroutineDispatcher.
     * For example Dispatchers.setMain will accept a TestCoroutineDispatcher
     * but not a TestCoroutineScope.
     *
     * * TestCoroutineScope will always use a TestCoroutineDispatcher to execute coroutines.
     * It also uses TestCoroutineExceptionHandler to convert uncaught exceptions into test failures.
     *
     * * By providing TestCoroutineScope a test case is able to control execution of coroutines,
     * as well as ensure that uncaught exceptions
     * thrown by coroutines are converted into test failures.
     *
     * ### Note: TestCoroutineScope, TestCoroutineDispatcher, and TestCoroutineExceptionHandler are interfaces to enable test libraries to provide library specific integrations.
     * ### For example, a JUnit4 @Rule may call Dispatchers.setMain then expose TestCoroutineScope for use in tests.
     *
     */
    class TestCoroutineScopeTests {

        private val testScope = TestCoroutineScope()
        private lateinit var subject: Subject

        @Before
        fun setup() {
            // provide the scope explicitly, in this example using a constructor parameter
            subject = Subject(testScope)

            // 🔥🔥🔥 If this scope used instead of same scope tests fail
//            subject = Subject(CoroutineScope(SupervisorJob()))

        }

        @After
        fun cleanUp() {
            // 🔥🔥🔥 WITHOUT TRY-CATCH TEST CRASHES when it throws exception!!!
            try {
                testScope.cleanupTestCoroutines()
            } catch (e: Exception) {
                //Do something here
            }
        }

        @Test
        fun testFoo() = testScope.runBlockingTest {
            // TestCoroutineScope.runBlockingTest uses the Dispatcher and exception handler provided by `testScope`
            val actual = subject.foo()
            Truth.assertThat(actual).isEqualTo(5)
        }

        @Test
        fun `Test subject that assigns value via coroutine`() = testScope.runBlockingTest {

            // GIVEN
            val subjectWithValue = SubjectWithValue(testScope)

            // WHEN
            subjectWithValue.getMockResponse()
            /*
                🔥🔥 Required to progress time beyond delay, because getMockResponse()
                function has another launch builder
             */
            advanceTimeBy(10_000)

            // THEN
            Truth.assertThat(subjectWithValue.result).isEqualTo(5)
        }

        /**
         * 🔥🔥 For this test to not crash put `  testScope.cleanupTestCoroutines()`
         * inside **try-catch** block
         */
        @Test(expected = RuntimeException::class)
        fun `Exception with delay`() = testScope.runBlockingTest {

            // GIVEN
            val subjectWithValue = SubjectWithValue(testScope)

            // WHEN
            subjectWithValue.throwExceptionAfterDelay()
        }

        /**
         * ❌ NOT WORKING, time is progressed after delay !!!
         */
        @Test(expected = TimeoutCancellationException::class)
        fun `Exception after time out`() = testScope.runBlockingTest {

            // GIVEN
            val subjectWithValue = SubjectWithValue(testScope)

            // WHEN
            subjectWithValue.getMockResponseWithTimeout()
            advanceTimeBy(4_000)

            // THEN
            println("Test result: ${subjectWithValue.result}")

        }


        class Subject(private val scope: CoroutineScope) {

            /**
             * This function does not work with delay
             */
            fun foo(): Int {
                var res = 0
                scope.launch {
                    // launch uses the testScope injected in setup
//                    delay(2000)
                    res = 5
                }
                return res
            }

        }

        class SubjectWithValue(private val scope: CoroutineScope) {

            var result = -1

            fun getMockResponse() {

                result = 0

                scope.launch {
                    result = getDelayedResponse()
                }

            }

            /**
             * Function that gets result if it's before timeout, after timeout it throws
             * [TimeoutCancellationException]
             */
            fun getMockResponseWithTimeout() {
                result = -10
                scope.launch {

                    println("🤪 getMockResponseWithTimeout() thread: ${Thread.currentThread().name}, in launch scope: $scope")
                    withTimeout(3_000) {
                        println("🙄 getMockResponseWithTimeout() thread: ${Thread.currentThread().name}, in timeout scope: $scope")
                        result = getDelayedResponse()
                    }
                }
            }

            fun throwExceptionAfterDelay() {

                scope.launch {
                    println("⛱ fooWithException() thread: ${Thread.currentThread().name}, scope: $this")
                    delay(6_000)
                    println("😎 fooWithException() After delay")
                    throw RuntimeException("Failed via TEST exception")
                }
            }

            private suspend fun getDelayedResponse(): Int {
                println("😱 getDelayedResponse() BEFORE DELAY")
                delay(10_000)
                println("😱 getDelayedResponse() AFTER DELAY")
                return 5
            }

        }
    }


    /**
     * ### Providing an explicit TestCoroutineDispatcher
     */
    class TestCoroutineDispatcherTests {

        private val testDispatcher = TestCoroutineDispatcher()

        @Before
        fun setup() {
            // provide the scope explicitly, in this example using a constructor parameter
            Dispatchers.setMain(testDispatcher)
        }

        @After
        fun cleanUp() {
            Dispatchers.resetMain()
            try {
                testDispatcher.cleanupTestCoroutines()
            } catch (e: Exception) {

            }
        }

        @Test
        fun testSomeFunction() = testDispatcher.runBlockingTest {
            // TestCoroutineDispatcher.runBlockingTest uses `testDispatcher` to run coroutines
            someFunction()
        }

        // TODO CRASHES BECAUSE this function uses MainScope
        @Test(expected = RuntimeException::class)
        fun testSomeFunctionWithException() = testDispatcher.runBlockingTest {
            someFunctionWithException()
        }


        /**
         * This function PASSES because it uses the same scope
         * [TestCoroutineDispatcher.runBlockingTest] runs in
         */
        @Test(expected = RuntimeException::class)
        fun `Test exception with function with same scope`() = testDispatcher.runBlockingTest {
            anotherFunctionWithException()
        }

        private fun someFunction() {
            MainScope().launch {
                // launch will use the testDispatcher provided by setMain
            }
        }

        /**
         * Test using this method fails since it has is own scope
         */
        private fun someFunctionWithException() {
            MainScope().launch {
                throw RuntimeException("Failed via TEST exception")
            }
        }

        /**
         * Using the same scope as tests have make it possible to have passed tests
         */
        private fun CoroutineScope.anotherFunctionWithException() {
            launch {
                throw RuntimeException("Failed via TEST exception")
            }
        }

    }

    class TestCoroutineScopeAndTestCoroutineDispatcherWithoutRunBlockingTest {

        @Test
        fun testFooWithAutoProgress() {
            val scope = TestCoroutineScope()

            scope.launch {
                val res = foo()
                // foo is suspended waiting for time to progress
                scope.advanceUntilIdle()
                // foo's coroutine will be completed before here
                Truth.assertThat(res).isEqualTo(5)
            }

        }

        private suspend fun foo(): Int {

            var result = 0
            println(1)            // executes eagerly when foo() is called due to TestCoroutineScope
            delay(1_000)          // suspends until time is advanced by at least 1_000
            println(2)            // executes after advanceTimeUntilIdle

            return 3
        }

    }

}


