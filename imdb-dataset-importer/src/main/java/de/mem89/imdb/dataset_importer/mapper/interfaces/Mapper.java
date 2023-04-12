package de.mem89.imdb.dataset_importer.mapper.interfaces;

public interface Mapper<FROM,TO> {
    public TO map(FROM source);
}
