/*
 * Copyright 2017-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.koin.dsl

import org.koin.core.instance.InstanceFactory
import org.koin.core.instance.newInstance
import org.koin.core.module.Module
import org.koin.core.qualifier.Qualifier

/**
 * Create a Single definition for given type T
 * @param qualifier
 * @param override - allow definition override
 */
inline fun <reified R : Any> ScopeDSL.scoped(
    qualifier: Qualifier? = null
): Pair<Module, InstanceFactory<R>> {
    return scoped(qualifier) { newInstance(R::class) }
}

/**
 * Create a Factory definition for given type T
 *
 * @param qualifier
 * @param override - allow definition override
 */
inline fun <reified R : Any> ScopeDSL.factory(
    qualifier: Qualifier? = null
): Pair<Module, InstanceFactory<R>> {
    return factory(qualifier) { newInstance(R::class) }
}