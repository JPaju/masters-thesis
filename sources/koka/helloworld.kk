effect logging
    fun log(msg : string) : ()

fun console-logging(action)
    with handler fun log(msg) println(msg)
    action()


fun collect-logging(action)
    var logs := []

    with handler
      return(x) logs.reverse
      fun log(msg) logs := Cons(msg, logs)

    action()

fun program-that-logs() : logging ()
    log("Starting program")
    // Do the program
    log("Program ended")
    log("The result is: " ++ show(42))


fun main()
    with console-logging
    val all-logs = program-that-logs()

    println(all-logs)
