package uz.qodirov.warehouse.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PaginationUtil {
    public Pageable createPageable(int page, int size) {
        return size == -1 ? Pageable.unpaged() : PageRequest.of(page, size);
    }

    public Map<String, Object> createMeta(long totalElements, int page, int size, int totalPage) {
        Map<String, Object> meta = new HashMap<>();
        boolean unpaged = size == -1;

        meta.put("totalElements", totalElements);
        meta.put("totalPages", unpaged ? 1 : totalPage);
        meta.put("currentPage", unpaged ? 0 : page);
        meta.put("pageSize", size);

        return meta;
    }

}
