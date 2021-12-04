/*
 * Copyright 2018 Zhenjie Yan.
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
package com.github.xwanlion.lifeauctioneer.andserver;

import androidx.annotation.NonNull;

import com.github.xwanlion.lifeauctioneer.util.CookieUtils;
import com.github.xwanlion.lifeauctioneer.util.JsonUtils;
import com.github.xwanlion.lifeauctioneer.util.Logger;
import com.yanzhenjie.andserver.annotation.Interceptor;
import com.yanzhenjie.andserver.framework.HandlerInterceptor;
import com.yanzhenjie.andserver.framework.body.StringBody;
import com.yanzhenjie.andserver.framework.handler.RequestHandler;
import com.yanzhenjie.andserver.http.HttpMethod;
import com.yanzhenjie.andserver.http.HttpRequest;
import com.yanzhenjie.andserver.http.HttpResponse;
import com.yanzhenjie.andserver.util.MultiValueMap;

import cn.hutool.http.body.RequestBody;

/**
 * Created by Zhenjie Yan on 2018/9/11.
 */
@Interceptor
public class LoggerInterceptor implements HandlerInterceptor {

    @Override
    public boolean onIntercept (
        @NonNull HttpRequest request,
        @NonNull HttpResponse response,
        @NonNull RequestHandler handler
    ) {
        String path = request.getPath();
        HttpMethod method = request.getMethod();
        MultiValueMap<String, String> valueMap = request.getParameter();
        // Logger.i(method.value() + " " + path + " by param: " + JsonUtils.toJsonString(valueMap));
        return false;
    }
}