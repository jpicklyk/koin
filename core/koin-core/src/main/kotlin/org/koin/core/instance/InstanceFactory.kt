package org.koin.core.instance

import org.koin.core.parameter.ParameterDefinition
import org.koin.dsl.definition.BeanDefinition
import org.koin.error.BeanInstanceCreationException

/**
 * Instance factory - handle objects creation against BeanRegistry
 * @author - Arnaud GIULIANI
 * @author - Laurent BARESSE
 */
class InstanceFactory {

    val instances = HashMap<BeanDefinition<*>, Any>()

    /**
     * Retrieve or create bean instance
     * @return Instance / has been created
     */
    fun <T> retrieveInstance(def: BeanDefinition<*>, p: ParameterDefinition): Pair<T, Boolean> {
        // Factory
        return if (def.isNotASingleton()) {
            Pair(createInstance(def, p), true)
        } else {
            // Singleton
            val found: T? = findInstance<T>(def)
            val instance: T = found ?: createInstance(def, p)
            val created = found == null
            if (created) {
                saveInstance(def, instance)
            }
            Pair(instance, created)
        }
    }

    private fun <T> saveInstance(def: BeanDefinition<*>, instance: T) {
        instances[def] = instance as Any
    }

    /**
     * Find existing instance
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T> findInstance(def: BeanDefinition<*>): T? {
        val existingClass = instances.keys.firstOrNull { it == def }
        return if (existingClass != null) {
            instances[existingClass] as? T
        } else {
            null
        }
    }

    /**
     * create instance for given bean definition
     */
    @Suppress("UNCHECKED_CAST")
    private fun <T> createInstance(def: BeanDefinition<*>, p: ParameterDefinition): T {
        try {
            val parameterList = p()
            val instance = def.definition.invoke(parameterList) as Any
            instance as T
            return instance
        } catch (e: Throwable) {
            e.printStackTrace()
            throw BeanInstanceCreationException("Can't create bean $def due to error :\n\t$e")
        }
    }

    /**
     * Drop all instances for definitions
     */
    fun releaseInstances(definitions: List<BeanDefinition<*>>) {
        definitions.forEach { instances.remove(it) }
    }

    /**
     * Clear all resources
     */
    fun clear() {
        instances.clear()
    }

}