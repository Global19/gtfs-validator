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

package org.mobilitydata.gtfsvalidator.domain.entity.notice.error;

import org.mobilitydata.gtfsvalidator.domain.entity.notice.NoticeExporter;
import org.mobilitydata.gtfsvalidator.domain.entity.notice.base.ErrorNotice;

import java.io.IOException;

public class FeedInfoLangAgencyLangMismatchNotice extends ErrorNotice {

    public FeedInfoLangAgencyLangMismatchNotice(final String agencyId,
                                                final String agencyName,
                                                final String agencyLang,
                                                final String feedInfoFeedLang) {
        super("feed_info.txt",
                E_055,
                "Mismatching feed and agency language fields",
                String.format("Agency with `agency_name`: `%s` have mismatching `agency.agency_lang`: `%s` and" +
                                " feed_info.feed_lang`: `%s`.",
                        agencyName,
                        agencyLang,
                        feedInfoFeedLang),
                agencyId
        );
        putNoticeSpecific(KEY_AGENCY_AGENCY_NAME, agencyName);
        putNoticeSpecific(KEY_AGENCY_AGENCY_LANG, agencyLang);
        putNoticeSpecific(KEY_FEED_INFO_FEED_LANG, feedInfoFeedLang);
    }

    @Override
    public void export(final NoticeExporter exporter) throws IOException {
        exporter.export(this);
    }
}