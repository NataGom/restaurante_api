package co.edu.umanizales.restaurante_api.repository;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

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
                long maxId = 0;
                for (T entity : entities) {
                    long id = getId(entity);
                    if (id > maxId) {
                        maxId = id;
                    }
                }
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
            List<T> toRemove = new ArrayList<>();
            for (T e : entities) {
                if (getId(e) == getId(entity)) {
                    toRemove.add(e);
                }
            }
            entities.removeAll(toRemove);
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

            List<T> result = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean isFirstLine = true;
                while ((line = reader.readLine()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue; // Skip header
                    }
                    if (!line.trim().isEmpty()) {
                        result.add(fromCsv(line));
                    }
                }
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
    }

    /**
     * Find entity by ID
     */
    public T findById(long id) {
        List<T> entities = findAll();
        for (T entity : entities) {
            if (getId(entity) == id) {
                return entity;
            }
        }
        return null;
    }

    /**
     * Delete entity by ID
     */
    public boolean deleteById(long id) {
        try {
            List<T> entities = findAll();
            T toRemove = null;
            for (T e : entities) {
                if (getId(e) == id) {
                    toRemove = e;
                    break;
                }
            }
            
            boolean removed = false;
            if (toRemove != null) {
                removed = entities.remove(toRemove);
                if (removed) {
                    writeAll(entities);
                }
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
