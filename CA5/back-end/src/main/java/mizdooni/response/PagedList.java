package mizdooni.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import mizdooni.response.serializer.ListSizeSerializer;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

public class PagedList<T> {
    @JsonProperty
    private int page;
    private int size;
    private int start;
    private int end;
    private int totalSize;
    @JsonProperty("size")
    @JsonSerialize(using = ListSizeSerializer.class)
    private List<T> pageList;

    private PagedList(int totalSize, int page, int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("invalid page number");
        }
        this.page = page;
        this.size = size;
        this.totalSize = totalSize;
        this.start = (page - 1) * size;
        this.end = Math.min(start + size, totalSize);
    }

    public PagedList(List<T> original, int page, int size) {
        this(original.size(), page, size);
        if (this.start >= original.size()) {
            this.pageList = Collections.emptyList();
        } else {
            this.pageList = original.subList(start, end);
        }
    }

    public PagedList(List<T> pageList, int totalSize, PageRequest pageRequest) {
        this(totalSize, pageRequest.getPageNumber() + 1, pageRequest.getPageSize());
        this.pageList = pageList;
    }

    @JsonProperty
    public boolean hasNext() {
        return end < totalSize;
    }

    @JsonProperty
    public int totalPages() {
        return (int) Math.ceil((double) totalSize / size);
    }

    @JsonProperty("pageList")
    public List<T> getPageList() {
        return pageList;
    }
}
