package datev.ddd.example.Onion.infrastructure.repository.mongodb;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.config.Defaults;
import de.flapdoodle.embed.mongo.config.MongodConfig;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.RuntimeConfig;
import de.flapdoodle.embed.process.extract.DirectoryAndExecutableNaming;
import de.flapdoodle.embed.process.extract.NoopTempNaming;
import de.flapdoodle.embed.process.io.directories.Directory;
import de.flapdoodle.embed.process.io.directories.FixedPath;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;

import static de.flapdoodle.embed.mongo.config.Defaults.downloadConfigFor;

@Configuration
public class EmbedMongoConfiguration {
    private static final Command MONGO_D = Command.MongoD;
    private static final String DOWNLOAD_LOCATION = "https://git.zd.datev.de/webappcommunity/tools/droplocation/raw/master/mongodb/";

    private static final String ABSOLUTE_EXTRACTION_PATH_FROM_RELATIVE = Paths.get("target/embedmongo")
                                                                              .toAbsolutePath()
                                                                              .toString();
    private static final Directory ARTIFACT_STORE_PATH = new FixedPath(ABSOLUTE_EXTRACTION_PATH_FROM_RELATIVE);

    @Bean
    public RuntimeConfig runtimeConfig() {
        return Defaults.runtimeConfigFor(MONGO_D)
                       .artifactStore(Defaults.extractedArtifactStoreFor(MONGO_D)
                                              .withDownloadConfig(downloadConfigFor(MONGO_D)
                                                      .downloadPath(__ -> DOWNLOAD_LOCATION)
                                                      .artifactStorePath(ARTIFACT_STORE_PATH)
                                                      .build())
                                              .withExtraction(DirectoryAndExecutableNaming.of(ARTIFACT_STORE_PATH, new NoopTempNaming()))
                       )
                       .build();
    }

    @Bean
    public MongodConfig versionConfig() {
        return MongodConfig.builder().version(Version.V4_0_12).build();
    }
}
