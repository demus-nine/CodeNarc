/*
 * Copyright 2010 the original author or authors.
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
package org.codenarc.rule.unnecessary

import org.codenarc.rule.AbstractRuleTestCase
import org.codenarc.rule.Rule

/**
 * Tests for UnnecessaryFloatInstantiationRule
 *
 * @author Hamlet D'Arcy
 * @version $Revision$ - $Date$
 */
class UnnecessaryFloatInstantiationRuleTest extends AbstractRuleTestCase {

    void testRuleProperties() {
        assert rule.priority == 2
        assert rule.name == "UnnecessaryFloatInstantiation"
    }

    void testSuccessScenario() {
        final SOURCE = '''
            assert 0.42f == foo()
            assert 0.42f == new Float([] as char[])
            assert 42.10f == new Float("42.10", 10)
        '''
        assertNoViolations(SOURCE)
    }

    void testStringConstructor() {
        final SOURCE = '''
            new Float("42.10")
        '''
        assertSingleViolation(SOURCE, 2, 'new Float("42.10")', "Can probably be rewritten as 42.10f")
    }

    void testDoubleConstructor() {
        final SOURCE = '''
            new Float(42.10d)
        '''
        assertSingleViolation(SOURCE, 2, 'new Float(42.10d)', "Can probably be rewritten as 42.1f")
    }

    void testFloatConstructor() {
        final SOURCE = '''
            new Float(42.10f)
        '''
        assertSingleViolation(SOURCE, 2, 'new Float(42.10f)', "Can probably be rewritten as 42.1f")
    }

    protected Rule createRule() {
        new UnnecessaryFloatInstantiationRule()
    }
}