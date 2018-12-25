package net.octyl.kiavi

inline class Foo(val str: String)

interface FooProvider {
    var theString: String
    var theFoo: Foo
}
