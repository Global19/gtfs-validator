package org.mobilitydata.gtfsvalidator.db;

import com.google.common.io.Resources;
import com.google.protobuf.TextFormat;
import org.mobilitydata.gtfsvalidator.adapter.protos.GtfsSpecificationProto;
import org.mobilitydata.gtfsvalidator.domain.entity.RawFileInfo;
import org.mobilitydata.gtfsvalidator.parser.GtfsEntityParser;
import org.mobilitydata.gtfsvalidator.usecase.port.GtfsSpecRepository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryGtfsSpecRepository implements GtfsSpecRepository {

    private final GtfsSpecificationProto.CsvSpecProtos inMemoryGTFSSpec;

    public InMemoryGtfsSpecRepository(final String specResourceName) throws IOException {
        //noinspection UnstableApiUsage
        inMemoryGTFSSpec = TextFormat.parse(Resources.toString(Resources.getResource(specResourceName), StandardCharsets.UTF_8),
                GtfsSpecProto.CsvSpecProtos.class);
    }

    @Override
    public List<String> getRequiredFilenameList() {
        return inMemoryGTFSSpec.getCsvspecList().stream()
                .filter(GtfsSpecificationProto.CsvSpecProto::getRequired).map(GtfsSpecificationProto.CsvSpecProto::getFilename)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getOptionalFilenameList() {
        return inMemoryGTFSSpec.getCsvspecList().stream()
                .filter(file -> !file.getRequired())
                .map(GtfsSpecProto.CsvSpecProto::getFilename)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getRequiredHeadersForFile(RawFileInfo fileInfo) {
        GtfsSpecificationProto.CsvSpecProto specForFile = getSpecForFile(fileInfo);

        if (specForFile != null) {
            return specForFile.getColumnList().stream()
                    .filter(GtfsSpecificationProto.ColumnSpecProto::getRequired)
                    .map(GtfsSpecificationProto.ColumnSpecProto::getName)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public List<String> getOptionalHeadersForFile(RawFileInfo fileInfo) {
        GtfsSpecificationProto.CsvSpecProto specForFile = getSpecForFile(fileInfo);

        if (specForFile != null) {
            return specForFile.getColumnList().stream()
                    .filter(column -> !column.getRequired())
                    .map(GtfsSpecificationProto.ColumnSpecProto::getName)
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    private GtfsSpecificationProto.CsvSpecProto getSpecForFile(RawFileInfo fileInfo) {
        return inMemoryGTFSSpec.getCsvspecList().stream()
                .filter(spec -> fileInfo.getFilename().equals(spec.getFilename()))
                .findAny()
                .orElse(null);
    }


    @Override
    public RawEntityParser getParserForFile(RawFileInfo file) {
        return new GtfsEntityParser(
                inMemoryGTFSSpec.getCsvspecList().stream()
                        .filter(spec -> file.getFilename().equals(spec.getFilename()))
                        .findAny()
                        .orElse(null),
                file);
    }
}
