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

package com.nextstak.userhub.repository;

import com.nextstak.userhub.domain.Identifiable;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class InMemoryRepository<T extends Identifiable> {

    @Autowired
    private IdGenerator idGenerator;

    private List<T> elements = Collections.synchronizedList(new ArrayList<>());

    public T create(T element) {
        elements.add(element);
        element.setId(idGenerator.getNextId());
        return element;
    }

    public boolean delete(Long id) {
        return elements.removeIf(element -> element.getId().equals(id));
    }

    public List<T> findAll() {
        return elements;
    }

    public Optional<T> findById(Long id) {
        return elements.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public int getCount() {
        return elements.size();
    }

    public void clear() {
        elements.clear();
    }

    public boolean update(Long id, T updated) {

        if (updated == null) {
            return false;
        }
        else {
            Optional<T> element = findById(id);
            element.ifPresent(original -> updateIfExists(original, updated));
            return element.isPresent();
        }
    }

    protected abstract void updateIfExists(T original, T desired);

}
