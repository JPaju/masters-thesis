import std/os/readline

// effect user-interaction
//     fun say(msg : string) : ()
//     ctl ask() : string

effect fun say(msg : string) : ()
effect ctl ask() : string
alias user-interaction = <ask, say>

val console-say = handler fun say(msg : string) println(msg)
val console-ask = handler fun ask() readline()

fun const-ask(answer, action)
  with fun ask() answer
  action()

fun ask-twice(action)
  with handler
    return(x) [x]
    // ctl ask() resume("First") ++ resume("Second")
    ctl ask() resume(readline()) ++ resume(readline())
  action()

fun console-user-interaction(action)
  // with const-ask("Yass")
  // with console-ask
  with ask-twice
  with console-say
  action()

fun greet-program(): user-interaction ()
  say("What's your name?")
  val name = ask()
  say("Hello " ++ name)

// fun main(): <console, exn> list<()>
fun main()
  // with console-user-interaction

  greet-program()
  // count(name)
  // ()
