package work.racka.thinkrchive.v2

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}