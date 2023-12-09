package com.eventico.model;

import com.eventico.model.entity.City;
import com.eventico.model.entity.Country;

import java.util.List;

public class CityCountryModel {
    private List<City> cities;
    private List<Country> countries;

    public CityCountryModel(List<City> cities, List<Country> countries) {
        this.cities = cities;
        this.countries = countries;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }
}
