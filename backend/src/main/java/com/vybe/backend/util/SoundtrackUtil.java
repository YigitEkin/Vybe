package com.vybe.backend.util;


import com.fasterxml.jackson.core.JsonFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class SoundtrackUtil {

    private static WebClient webClient = WebClient.create();
    private static HttpGraphQlClient graphQlClient = HttpGraphQlClient.builder(webClient)
            .build();

    @Value("${soundtrackyourbrand.api.url}")
    private static String url;

    public static List<String> getTracksOnPlaylist(String playlistId, String token) {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("playlistId", playlistId);
        variables.put("first", 1000);
        String document = """
                query Playlist($playlistId: ID!, $first: Int) {
                  playlist(id: $playlistId) {
                    tracks(first: $first) {
                      edges {
                        node {
                          title
                        }
                      }
                    }
                  }
                }
                """;
        Object response = (LinkedHashMap) graphQlClient.mutate()
                .header("Authorization", "Basic " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Connection", "keep-alive")
                .url(url)
                .build()
                .document(document)
                .variables(variables)
                .retrieve("")
                .toEntity(Object.class)
                .block();
        LinkedHashMap<String, Object> playlist = (LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) response).get("playlist");
        LinkedHashMap<String, Object> tracks = (LinkedHashMap<String, Object>) (playlist).get("tracks");
        ArrayList<LinkedHashMap<String, Object>> edges = (ArrayList<LinkedHashMap<String, Object>> ) ((LinkedHashMap<String, Object>) tracks).get("edges");
        ArrayList<String> trackNames = new ArrayList<>();
        for (LinkedHashMap<String, Object> edge : edges) {
            LinkedHashMap<String, Object> node = (LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) edge).get("node");
            String trackName = (String) ((LinkedHashMap<String, Object>) node).get("title");
            trackNames.add(trackName);
        }
        return trackNames;
    }
    public static int findIndexOfSongInPlaylist(String playlistId, String songName, String token) {
        List<String> trackNames = getTracksOnPlaylist(playlistId, token);
        for (int i = 0; i < trackNames.size(); i++) {
            if (trackNames.get(i).equals(songName)) {
                return i;
            }
        }
        return -1;
    }
    public static boolean playSong(String playlistId, int index, List<String> soundZones, String token) {
        HashMap<String, Object> variables = new HashMap<>();
        HashMap<String, Object> input = new HashMap<>();

        input.put("source", playlistId);
        input.put("sourceTrackIndex", index);
        input.put("soundZones", soundZones);
        input.put("immediate", true);
        variables.put("input", input);
        String document = """
                mutation Mutation($input: SoundZoneAssignSourceInput!) {
                  soundZoneAssignSource(input: $input) {
                    soundZones
                  }
                }
                """;
        Object response = (LinkedHashMap) graphQlClient.mutate()
                .header("Authorization", "Basic " + token)
                .header("Content-Type", "application/json")
                .header("Accept", "*/*")
                .header("Accept-Encoding", "gzip, deflate, br")
                .header("Connection", "keep-alive")
                .url(url)
                .build()
                .document(document)
                .variables(variables)
                .retrieve("")
                .toEntity(Object.class)
                .block();

        return response != null;
    }

}
