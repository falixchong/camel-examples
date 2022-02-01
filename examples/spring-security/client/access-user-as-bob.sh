#!/bin/bash
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

WORK_DIR=../target/work
rm -rf $WORK_DIR
mkdir -p $WORK_DIR
wget --http-user=bob --http-password=bobspassword --directory-prefix=$WORK_DIR  --output-file=$WORK_DIR/log.txt http://localhost:8080/camel-example-spring-security/camel/user
cat -n $WORK_DIR/* | less