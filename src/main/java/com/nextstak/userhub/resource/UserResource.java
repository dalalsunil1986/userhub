/*
 *
 *  * Licensed to the Apache Software Foundation (ASF) under one
 *  * or more contributor license agreements. See the NOTICE file
 *  * distributed with this work for additional information
 *  * regarding copyright ownership. The ASF licenses this file
 *  * to you under the Apache License, Version 2.0 (the
 *  * "License"); you may not use this file except in compliance
 *  * with the License. You may obtain a copy of the License at
 *  *
 *  * http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing,
 *  * software distributed under the License is distributed on an
 *  * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  * KIND, either express or implied. See the License for the
 *  * specific language governing permissions and limitations
 *  * under the License.
 *
 *
 */

package com.nextstak.userhub.resource;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nextstak.userhub.domain.User;
import org.springframework.hateoas.ResourceSupport;

public class UserResource extends ResourceSupport {

    private final long id;
    private final String description;
    private final long costInCents;
    private final boolean isComplete;

    public UserResource(User user) {
        this.id = user.getId();
        this.description = user.getDescription();
        this.costInCents = user.getCostInCents();
        this.isComplete = user.isComplete();
    }

    @JsonProperty("id")
    public Long getResourceId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public long getCostInCents() {
        return costInCents;
    }

    public boolean isComplete() {
        return isComplete;
    }


}
