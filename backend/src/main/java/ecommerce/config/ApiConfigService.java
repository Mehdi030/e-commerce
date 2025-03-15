package ecommerce.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ApiConfigService {

    @Value("${shoppingcart.api.url}")
    private String apiUrl;

    public String getApiUrl() {
        return apiUrl;
    }
}
