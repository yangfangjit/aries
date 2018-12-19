/*
 * Copyright 2018-2018 the original author or authors.
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

package com.yangfang.aries.dubbo.consumer.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yangfang.aires.dubbo.provider.service.api.AnnotateService;
import com.yangfang.aries.dubbo.consumer.base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

/**
 * TODO 类描述
 *
 * @author 幽明
 * @serial 2018/12/19
 */
@Slf4j
public class TestConsumer extends BaseTest {

    @Reference
    private AnnotateService annotateService;

    @Test
    public void TestConsumeAnnotateService() {
        log.info("call annotateService.helloDubbo() {}", annotateService.helloDubbo());
        Assert.assertEquals("Hello Dubbo", annotateService.helloDubbo());
    }
}


