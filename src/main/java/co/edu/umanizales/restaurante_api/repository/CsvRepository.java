package co.edu.umanizales.restaurante_api.repository;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Base repository class for CSV file operations
 */
public abstract class CsvRepository<T> {
    protected final String filePath;
    protected final AtomicLong idGenerator;

    public CsvRepository(String filePath) {
        this.filePath = filePath;
        this.idGenerator = new AtomicLong(1);
        initializeFile();
        initializeIdGenerator();
    }

    /**
     * Initialize CSV file with headers if it doesn't exist
     */
    private void initializeFile() {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            
            if (!file.exists()) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(getHeaders());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error initializing CSV file: " + filePath, e);
        }
    }

    /**
     * Initialize ID generator with the max ID from existing records
     */
    private void initializeIdGenerator() {
        try {
            List<T> entities = findAll();
            if (!entities.isEmpty()) {
                long maxId = entities.stream()
                    .mapToLong(this::getId)
                    .max()
                    .orElse(0);
                idGenerator.set(maxId + 1);
            }
        } catch (Exception e) {
            // If there's an error reading, start from 1
            idGenerator.set(1);
        }
    }

    /**
     * Save entity to CSV
     */
    public T save(T entity) {
        try {
            if (getId(entity) == 0) {
                setId(entity, idGenerator.getAndIncrement());
            }
            
            List<T> entities = findAll();
            entities.removeIf(e -> getId(e) == getId(entity));
            entities.add(entity);
            
            writeAll(entities);
            return entity;
        } catch (IOException e) {
            throw new RuntimeException("Error saving entity", e);
        }
    }

    /**
     * Find all entities
     */
    public List<T> findAll() {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                return new ArrayList<>();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                return reader.lines()
                    .skip(1) // Skip header
                    .filter(line -> !line.trim().isEmpty())
                    .map(this::fromCsv)
                    .collect(Collectors.toList());
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
    }

    /**
     * Find entity by ID
     */
    public Optional<T> findById(long id) {
        return findAll().stream()
            .filter(entity -> getId(entity) == id)
            .findFirst();
    }

    /**
     * Delete entity by ID
     */
    public boolean deleteById(long id) {
        try {
            List<T> entities = findAll();
            boolean removed = entities.removeIf(e -> getId(e) == id);
            
            if (removed) {
                writeAll(entities);
            }
            
            return removed;
        } catch (IOException e) {
            throw new RuntimeException("Error deleting entity", e);
        }
    }

    /**
     * Write all entities to CSV
     */
    protected void writeAll(List<T> entities) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(getHeaders());
            writer.newLine();
            
            for (T entity : entities) {
                writer.write(toCsv(entity));
                writer.newLine();
            }
        }
    }

    /**
     * Get CSV headers
     */
    protected abstract String getHeaders();

    /**
     * Convert entity to CSV line
     */
    protected abstract String toCsv(T entity);

    /**
     * Convert CSV line to entity
     */
    protected abstract T fromCsv(String csvLine);

    /**
     * Get entity ID
     */
    protected abstract long getId(T entity);

    /**
     * Set entity ID
     */
    protected abstract void setId(T entity, long id);

    /**
     * Escape CSV field
     */
    protected String escapeCsv(String field) {
        if (field == null) {
            return "";
        }
        if (field.contains(",") || field.contains("\"") || field.contains("\n")) {
            return "\"" + field.replace("\"", "\"\"") + "\"";
        }
        return field;
    }

    /**
     * Unescape CSV field
     */
    protected String unescapeCsv(String field) {
        if (field == null || field.isEmpty()) {
            return "";
        }
        if (field.startsWith("\"") && field.endsWith("\"")) {
            return field.substring(1, field.length() - 1).replace("\"\"", "\"");
        }
        return field;
    }
}
