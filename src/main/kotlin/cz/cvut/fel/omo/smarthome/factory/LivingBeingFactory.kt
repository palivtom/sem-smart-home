package cz.cvut.fel.omo.smarthome.factory

import cz.cvut.fel.omo.smarthome.model.livingbeing.Cat
import cz.cvut.fel.omo.smarthome.model.livingbeing.Dog
import cz.cvut.fel.omo.smarthome.model.livingbeing.Human
import cz.cvut.fel.omo.smarthome.model.livingbeing.LivingBeing

class LivingBeingFactory {
    fun createHuman(name: String? = null): Human {
        val human = Human()
        handleName(human, name)
        return human
    }

    fun createDog(name: String? = null): Dog {
        val dog = Dog()
        handleName(dog, name)
        return dog
    }

    fun createCat(name: String? = null): Cat {
        val cat = Cat()
        handleName(cat, name)
        return cat
    }

    private fun handleName(livingBeing: LivingBeing, name: String?) {
        if (name != null) livingBeing.name = name
    }
}