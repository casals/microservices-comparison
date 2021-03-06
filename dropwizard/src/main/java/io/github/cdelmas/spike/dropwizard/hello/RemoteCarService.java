/*
   Copyright 2015 Cyril Delmas

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package io.github.cdelmas.spike.dropwizard.hello;

import io.github.cdelmas.spike.common.domain.Car;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import java.util.List;

import static java.util.Arrays.asList;

public class RemoteCarService implements CarService {

    @Inject
    private Client client;

    @Context
    private Request request;

    @Override
    public List<Car> getAllCars(String auth) {
        WebTarget target = client.target("https://localhost:8443/app/cars");
        Invocation invocation = target.request(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + auth)
                .build(HttpMethod.GET);
        Car[] cars = invocation.invoke(Car[].class);
        return asList(cars);
    }

}
