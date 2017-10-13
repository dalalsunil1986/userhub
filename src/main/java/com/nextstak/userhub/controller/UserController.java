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

package com.nextstak.userhub.controller;

import com.nextstak.userhub.domain.User;
import com.nextstak.userhub.repository.UserRepository;
import com.nextstak.userhub.resource.UserResource;
import com.nextstak.userhub.resource.UserResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@ExposesResourceFor(User.class)
@RequestMapping(value = "/user", produces = "application/json")
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private UserResourceAssembler assembler;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Collection<UserResource>> findAllOrders() {
        List<User> users = repository.findAll();
        return new ResponseEntity<>(assembler.toResourceCollection(users), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<UserResource> createUser(@RequestBody User user) {
        User createdUser = repository.create(user);
        return new ResponseEntity<>(assembler.toResource(createdUser), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserResource> findUserById(@PathVariable Long id) {
        Optional<User> user = repository.findById(id);

        if (user.isPresent()) {
            return new ResponseEntity<>(assembler.toResource(user.get()), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean wasDeleted = repository.delete(id);
        HttpStatus responseStatus = wasDeleted ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(responseStatus);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<UserResource> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        boolean wasUpdated = repository.update(id, updatedUser);

        if (wasUpdated) {
            return findUserById(id);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
