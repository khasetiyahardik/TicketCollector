package com.example.TicketCollector.repository;

import com.example.TicketCollector.dto.SearchDTO;
import com.example.TicketCollector.dto.SearchResponseDTO;
import com.example.TicketCollector.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Slf4j
public class SearchRepository {
    @PersistenceContext
    EntityManager entityManager;

    private String GROUP_BY = " group by tme.id";

    public List<SearchResponseDTO> getSearchList(SearchDTO searchDTO) {
        Session session = entityManager.unwrap(Session.class);
        Long day = DateUtil.getDay(searchDTO.getDate());
        String whereClause = getWhereClause(searchDTO, day);
        String queryString = getSearchQuery() + whereClause + GROUP_BY;
        log.info("query string : " + queryString);
        Query<SearchResponseDTO> query1 = session.createSQLQuery(queryString).setResultTransformer(Transformers.aliasToBean(SearchResponseDTO.class));
        return query1.getResultList();
    }

    private String getWhereClause(SearchDTO searchDTO, Long day) {

        return " where (tme.travel_mode_number like '" + searchDTO.getName() + "%'" +
                "or tse.source like '" + searchDTO.getSource() + "%'" +
                "or tse.destination like '" + searchDTO.getDestination() + "%'" +
                "or sd.destination like '" + searchDTO.getDestination() + "%')" +
                "or (tme.arrival_time <= '" + searchDTO.getDate() + "'" + "  and tme.departure_time >= '" + searchDTO.getDate() + "'" + ") "
                + " AND dayofweek(DATE_ADD(tme.arrival_time, INTERVAL tme.interval_time_in_hour HOUR)) =" + day;
    }

    private String getSearchQuery() {
        return """
                  select
                      tme.id as tmId,
                      tme.travel_mode_number as travellingModeTitle,
                      DATE_ADD(tme.arrival_time,   INTERVAL tme.interval_time_in_hour HOUR) as arrivalTime,
                      DATE_ADD(tme.departure_time, INTERVAL tme.interval_time_in_hour HOUR) as departureTime,
                      tse.source as source,
                      tse.destination as destination
                        from
                      travelling_mode_entity tme
                        left join
                      travellingsdentity tse ON tse.tm_id = tme.id
                        left join
                      sdentity sd ON sd.sd_id = tse.sd_id
                """;

    }
}
