package com.team.animeapp.data.models;

public interface DomainMapper<Entity,Domain> {

    Domain toDomainModel(Entity entity);

    Entity fromDomainModel(Domain domainModel);
}
