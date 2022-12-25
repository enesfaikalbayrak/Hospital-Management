package com.albayrakenesfaik.extension.definition;

import com.albayrakenesfaik.extension.validation.BusinessException;
import com.albayrakenesfaik.extension.validation.BusinessExceptionKey;
import com.albayrakenesfaik.security.AuthoritiesConstants;
import com.albayrakenesfaik.security.SecurityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import tech.jhipster.service.Criteria;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Supplier;

public class Definition<T> {

    private T item;
    private DefinitionFilter definitionFilter;
    private Pageable pageable;
    private Criteria criteria;
    private Long id;
    private Map<DefinitionOption, Object> options;
    private Collection<? extends GrantedAuthority> authorities;

    public Definition(Builder<T> builder) {
        this.item = builder.item;
        this.definitionFilter = builder.definitionFilter;
        this.pageable = builder.pageable;
        this.criteria = builder.criteria;
        this.id = builder.id;
        this.options = builder.options;
        this.authorities = builder.authorities;
    }

    public T getItem() {
        return item;
    }

    public void setItem(T item) {
        this.item = item;
    }

    public Boolean isItemNull() {
        return item == null;
    }

    public DefinitionFilter getDefinitionFilter() {
        return definitionFilter;
    }

    public void setDefinitionFilter(DefinitionFilter definitionFilter) {
        this.definitionFilter = definitionFilter;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<DefinitionOption, Object> getOptions() {
        return options;
    }

    public void setOptions(Map<DefinitionOption, Object> options) {
        this.options = options;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void addOption(DefinitionOption key, Object value) {
        if (value == null) {
            throw new BusinessException(BusinessExceptionKey.OPTION_VALUE_NULL);
        }

        this.options.put(key, value);
    }

    public boolean hasOption(DefinitionOption key) {
        return this.options.containsKey(key);
    }

    public boolean hasOptionAndIsTrue(DefinitionOption key) {
        return this.hasOption(key) && this.getOption(key, Boolean.class);
    }

    public boolean hasOptionAndIsFalse(DefinitionOption key) {
        return this.hasOption(key) && !this.getOption(key, Boolean.class);
    }

    @SuppressWarnings("unchecked")
    public <R> R getOption(DefinitionOption definitionOption) {
        Object value = this.options.get(definitionOption);

        if (value == null) {
            return null;
        }

        return (R) value;
    }

    public <R> R getOption(DefinitionOption definitionOption, Class<R> clazz) {
        Object value = this.options.get(definitionOption);

        if (value == null) {
            return null;
        }

        return clazz.cast(value);
    }

    @SuppressWarnings("unchecked")
    public <R> R getOptionOrDefault(DefinitionOption definitionOption, R defaultValue) {
        Object value = this.options.get(definitionOption);

        if (value == null) {
            return defaultValue;
        }

        return (R) value;
    }

    public <R> R getOption(DefinitionOption definitionOption, R defaultValue, Class<R> clazz) {
        Object value = this.options.get(definitionOption);

        if (value == null) {
            return defaultValue;
        }

        return clazz.cast(value);
    }

    public static <T> Builder<T> of(T item) {
        return new Builder<>(item);
    }

    public static Builder<Void> list(Pageable pageable) {
        return new Builder<Void>().filter(DefinitionFilter.NO_FILTER).pageable(pageable);
    }

    public static Builder<Void> list(Pageable pageable, boolean unpaged) {
        return new Builder<Void>().filter(DefinitionFilter.NO_FILTER).pageable(unpaged ? Pageable.unpaged() : pageable);
    }

    public static Builder<Void> listAll() {
        return new Builder<Void>().filter(DefinitionFilter.NO_FILTER).pageable(Pageable.unpaged());
    }

    public static Builder<Void> listAll(Criteria criteria) {
        return new Builder<Void>().filter(DefinitionFilter.NO_FILTER).pageable(Pageable.unpaged()).criteria(criteria);
    }

    public static Builder<Void> listAll(Criteria criteria, Pageable pageable) {
        return new Builder<Void>().filter(DefinitionFilter.NO_FILTER).pageable(pageable).criteria(criteria);
    }

    public static Builder<Void> get(Long id) {
        return new Builder<Void>().id(id);
    }

    public static class Builder<T> {
        private T item = null;
        private DefinitionFilter definitionFilter = DefinitionFilter.NO_FILTER;
        private Pageable pageable;
        private Criteria criteria;
        private Long id;

        private final Map<DefinitionOption, Object> options = new HashMap<>();
        private final Collection<GrantedAuthority> authorities = new HashSet<>();

        public Builder() {
            super();
        }

        public Builder(T item) {
            super();
            this.item = item;
        }

        public Builder<T> option(DefinitionOption key, Object value) {
            if (value == null) {
                throw new BusinessException(BusinessExceptionKey.OPTION_VALUE_NULL);
            }

            this.options.put(key, value);
            return this;
        }

        public Builder<T> optionIfNotNull(DefinitionOption key, Object value) {
            if (value != null) {
                this.options.put(key, value);
            }

            return this;
        }

        public Builder<T> optionIf(DefinitionOption key, Object value, Supplier<Boolean> condition) {
            if (condition != null && condition.get()) {
                this.options.put(key, value);
            }

            return this;
        }

        public Builder<T> item(T item) {
            this.item = item;
            return this;
        }

        public Builder<T> filter(DefinitionFilter definitionFilter) {
            this.definitionFilter = definitionFilter;
            return this;
        }

        public Builder<T> pageable(Pageable pageable) {
            this.pageable = pageable;
            return this;
        }

        public Builder<T> criteria(Criteria criteria) {
            this.criteria = criteria;
            return this;
        }

        public Builder<T> id(Long id) {
            this.id = id;
            return this;
        }

        public Definition<T> build() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (authentication == null) {
                this.authorities.clear();
                this.authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
            } else {
                if (SecurityUtils.isAuthenticated()) {
                    this.authorities.addAll(authentication.getAuthorities());
                } else {
                    throw new BusinessException(BusinessExceptionKey.NOT_AUTHORIZED);
                }
            }

            return new Definition<>(this);
        }
    }
}
