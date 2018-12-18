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

package com.yangfang.aries.dubbo.provider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yangfang.aires.dubbo.provider.service.api.AnnotateService;
import org.springframework.stereotype.Component;

/**
 * TODO 类描述
 *
 * @author 幽明
 * @serial 2018/12/18
 */
@Service(interfaceClass = AnnotateService.class, timeout = 1000)
@Component
public class AnnotateServiceImpl implements AnnotateService {

    @Override
    public String helloDubbo() {
        return "Hello Dubbo";
    }

}
