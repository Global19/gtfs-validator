/*
 *  Copyright (c) 2020. MobilityData IO.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.mobilitydata.gtfsvalidator.usecase;

import org.junit.jupiter.api.Test;

class ValidateAgencyLangAndFeedInfoFeedLangMatchTest {

    @Test
    void notProvidedFeedInfoShouldNotGenerateNotice() {
        // todo feed_info.feed_lang = null
    }

    @Test
    void matchingFeedInfoFeedLangShouldNotGenerateNotice() {
        // todo, agency.agency_lang = feed_info.feed_lang
    }

    @Test
    void mulMatchesWithAnyAgencyLangAndShouldNotGenerateNotice() {
        // todo, feed_info.feed_lang = null and agency.agency_lang = whatever

    }

    @Test
    void nonMatchingFeedInfoFeedLangShouldGenerateNotice() {
        // todo: feed_info.feed_lang != agency.agency_lang
    }
}
