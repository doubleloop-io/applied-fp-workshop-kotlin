package io.doubleloop

import org.junit.jupiter.api.Test
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo

class Types {
    // # Types
    // In FP types are used to model/describe data and behavior intent.
    //   - Algebraic Data Type (ADT) to model data
    //   - Functions to model behaviors

    // ## Single Type Modelling

    // ### Proper types
    // es: Int, String

    // ### Type alias
    // rename a type without change it
    // (Nested/local type aliases are not supported in Kotlin)
    // typealias Name = String

    // ### Type wrapper
    // rename a type and change it
    data class Age(val value: Int)

    @Test
    fun `type wrapper`() {
        // the compiler automatically provides:

        // data constructor
        val age = Age(12)

        // getters
        expectThat(age.value).isEqualTo(age.value)

        // value equality
        val same = Age(12)
        expectThat(age).isEqualTo(same)

        // copy (aka setters for immutable world)
        val different = age.copy(value = 20)
        expectThat(age).isNotEqualTo(different)
    }

    // ## Composed Types

    // ### Product types
    // Put many types together e.g. struct in C, POJO in JAVA, POCO in C#.
    // Useful to model independent data in AND e.g. a Person is composed by a name *and* an age.
    data class Person(val name: String, val age: Int)
    // alternative version
    // data class Person(val name: String, val age: Age)

    @Test
    fun `product type`() {
        // the compiler automatically provides:
        // data constructor
        // getters
        // value equality
        // copy (aka setters for immutable world)
        val person = Person("John", 12)
        expectThat(person).isEqualTo(person)

        val same = Person("John", 12)
        expectThat(person).isEqualTo(same)

        val different = person.copy(name = "Jane")
        expectThat(person).isNotEqualTo(different)
    }

    // ### Sum types
    // Model exclusive types e.g. union in C, enum in JAVA/C#.
    // Useful to model dependant data in OR e.g. the Light is on *or* off.
    sealed class LightState {
        data class On(val intensity: Double) : LightState()
        data object Off : LightState()
    }

    @Test
    fun `sum type`() {
        // the compiler automatically provides:
        // data constructor
        // getters
        // value equality
        // copy (aka setters for immutable world)
        val light: LightState = LightState.On(0.5)
        expectThat(light).isEqualTo(light)

        // pay attention to the type inference LightState/On/Off are three different types
        val same: LightState = LightState.On(0.5)
        expectThat(light).isEqualTo(same)

        val different: LightState = LightState.Off
        expectThat(light).isNotEqualTo(different)
    }

    // ### Polymorphic Types
    // Types with generics are called: type constructor
    // While the generics labels are called: type parameters
    data class Triple<A, B, C>(val a: A, val b: B, val c: C)

    @Test
    fun `polymorphic types`() {
        val triple = Triple(1, "2", 3.0)
        expectThat(triple).isEqualTo(triple)

        val same = Triple(1, "2", 3.0)
        expectThat(triple).isEqualTo(same)

        val different = triple.copy(a = 2)
        expectThat(triple).isNotEqualTo(different)
    }
}