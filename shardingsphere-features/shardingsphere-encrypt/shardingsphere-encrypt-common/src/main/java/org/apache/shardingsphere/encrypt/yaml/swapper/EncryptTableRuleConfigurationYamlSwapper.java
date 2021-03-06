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

package org.apache.shardingsphere.encrypt.yaml.swapper;

import org.apache.shardingsphere.encrypt.api.config.EncryptColumnRuleConfiguration;
import org.apache.shardingsphere.encrypt.api.config.EncryptTableRuleConfiguration;
import org.apache.shardingsphere.encrypt.yaml.config.YamlEncryptColumnRuleConfiguration;
import org.apache.shardingsphere.encrypt.yaml.config.YamlEncryptTableRuleConfiguration;
import org.apache.shardingsphere.infra.yaml.swapper.YamlSwapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map.Entry;

/**
 * Encrypt table configuration YAML swapper.
 */
public final class EncryptTableRuleConfigurationYamlSwapper implements YamlSwapper<YamlEncryptTableRuleConfiguration, EncryptTableRuleConfiguration> {
    
    private final EncryptColumnRuleConfigurationYamlSwapper encryptColumnRuleConfigurationYamlSwapper = new EncryptColumnRuleConfigurationYamlSwapper();
    
    @Override
    public YamlEncryptTableRuleConfiguration swap(final EncryptTableRuleConfiguration data) {
        YamlEncryptTableRuleConfiguration result = new YamlEncryptTableRuleConfiguration();
        for (EncryptColumnRuleConfiguration each : data.getColumns()) {
            result.getColumns().put(each.getName(), encryptColumnRuleConfigurationYamlSwapper.swap(each));
        }
        return result;
    }
    
    @Override
    public EncryptTableRuleConfiguration swap(final YamlEncryptTableRuleConfiguration yamlConfiguration) {
        Collection<EncryptColumnRuleConfiguration> columns = new LinkedList<>();
        for (Entry<String, YamlEncryptColumnRuleConfiguration> entry : yamlConfiguration.getColumns().entrySet()) {
            YamlEncryptColumnRuleConfiguration yamlEncryptColumnRuleConfiguration = entry.getValue();
            yamlEncryptColumnRuleConfiguration.setLogicName(entry.getKey());
            columns.add(encryptColumnRuleConfigurationYamlSwapper.swap(yamlEncryptColumnRuleConfiguration));
        }
        return new EncryptTableRuleConfiguration(yamlConfiguration.getName(), columns);
    }
}
