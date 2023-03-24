package de.mem89.imdb.dataset_importer.mapper;

public interface Mapper<FROM,TO> {
    public TO map(FROM item);
}
