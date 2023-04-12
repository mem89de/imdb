package de.mem89.imdb.dataset_importer.configuration;

import java.util.List;


public record ConfigurationProperties(String filename, List<String> headers) {
}
