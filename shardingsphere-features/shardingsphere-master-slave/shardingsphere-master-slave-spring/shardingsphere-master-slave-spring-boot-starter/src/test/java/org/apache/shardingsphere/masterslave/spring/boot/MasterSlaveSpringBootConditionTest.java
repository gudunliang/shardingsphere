/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.masterslave.spring.boot;

import org.apache.shardingsphere.masterslave.spring.boot.condition.MasterSlaveSpringBootCondition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.mock.env.MockEnvironment;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class MasterSlaveSpringBootConditionTest {
    
    @Test
    public void assertNotMatch() {
        MockEnvironment mockEnvironment = new MockEnvironment();
        mockEnvironment.setProperty("spring.shardingsphere.rules.encrypt.encrypt-strategies.encrypt_strategy_aes.type", "aes");
        mockEnvironment.setProperty("spring.shardingsphere.rules.shadow.column", "user_id");
        ConditionContext context = Mockito.mock(ConditionContext.class);
        AnnotatedTypeMetadata metadata = Mockito.mock(AnnotatedTypeMetadata.class);
        when(context.getEnvironment()).thenReturn(mockEnvironment);
        MasterSlaveSpringBootCondition condition = new MasterSlaveSpringBootCondition();
        ConditionOutcome matchOutcome = condition.getMatchOutcome(context, metadata);
        assertThat(matchOutcome.isMatch(), is(false));
    }
    
    @Test
    public void assertMatch() {
        MockEnvironment mockEnvironment = new MockEnvironment();
        mockEnvironment.setProperty("spring.shardingsphere.rules.master-slave.data-sources.ds_ms.master-data-source-name", "ds_master");
        ConditionContext context = Mockito.mock(ConditionContext.class);
        AnnotatedTypeMetadata metadata = Mockito.mock(AnnotatedTypeMetadata.class);
        when(context.getEnvironment()).thenReturn(mockEnvironment);
        MasterSlaveSpringBootCondition condition = new MasterSlaveSpringBootCondition();
        ConditionOutcome matchOutcome = condition.getMatchOutcome(context, metadata);
        assertThat(matchOutcome.isMatch(), is(true));
    }
}

