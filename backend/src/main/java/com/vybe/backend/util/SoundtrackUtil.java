package com.vybe.backend.util;

import lombok.experimental.UtilityClass;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@UtilityClass
public class SoundtrackUtil {

    private static WebClient webClient = WebClient.create();
    private static HttpGraphQlClient graphQlClient = HttpGraphQlClient.builder(webClient)
            .build();

    //@Value("${soundtrackyourbrand.api.url}")
    private static final String url = "https://api.soundtrackyourbrand.com/v2";

    public static List<HashMap<String, String>> getTracksOnPlaylist(String playlistId, String token) {
        HashMap<String, Object> variables = new HashMap<>();
        variables.put("playlistId", playlistId);
        variables.put("first", 1000);
        variables.put("height", 100);
        variables.put("width", 100);
        variables.put("market", "TR");
        String document = """
                query Playlist($playlistId: ID!, $first: Int, $height: Int!, $width: Int!, $market: IsoCountry!) {
                  playlist(id: $playlistId) {
                    tracks(first: $first) {
                      edges {
                        node {
                          title
                          durationMs
                          album {
                            display {
                              image {
                                size(height: $height, width: $width)
                              }
                            }
                            isAvailable(market: $market)
                            
                          }
                          artists {
                            name
                          }
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
        ArrayList<HashMap<String, String>> trackList = new ArrayList<>();
        for (LinkedHashMap<String, Object> edge : edges) {
            LinkedHashMap<String, Object> node = (LinkedHashMap<String, Object>) ((LinkedHashMap<String, Object>) edge).get("node");
            String trackName = (String) ((LinkedHashMap<String, Object>) node).get("title");
            String duration = (String) ((LinkedHashMap<String, Object>) node).get("durationMs").toString();
            String imageUrl = "";
            try {
                imageUrl = ((LinkedHashMap<String, String>)((LinkedHashMap<String, Object>)((LinkedHashMap<String, Object>)node.get("album")).get("display")).get("image")).get("size");
            }
            catch (NullPointerException nullPointerException) {
                imageUrl = null;
            }
            String artistNames = ((ArrayList<LinkedHashMap<String, Object>>) node.get("artists")).stream().map(artist -> (String) artist.get("name")).reduce((a, b) -> a + ", " + b).get();
            HashMap<String, String> track = new HashMap<>();

            track.put("artistNames", artistNames);
            track.put("trackName", trackName);
            track.put("isAvailable", ((Boolean)((LinkedHashMap<String, Object>)node.get("album")).get("isAvailable")).toString());
            track.put("imageUrl", imageUrl);
            track.put("duration", duration);
            trackList.add(track);
        }
        return trackList;
    }
    public static HashMap<String, Integer> findIndexOfSongInPlaylist(String playlistId, String songName, String token) {
        List<HashMap<String, String>> trackNames = getTracksOnPlaylist(playlistId, token);
        for (int i = 0; i < trackNames.size(); i++) {
            if (trackNames.get(i).get("trackName").equals(songName)) {
                HashMap<String, Integer> track = new HashMap<>();
                track.put("index", i);
                track.put("duration", Integer.parseInt(trackNames.get(i).get("duration")));
                return track;
            }
        }
        throw new RuntimeException("Song not found in playlist");
    }
    public static boolean playSong(String playlistId, int index, List<String> soundZones, String token) {
        HashMap<String, Object> variables = new HashMap<>();
        HashMap<String, Object> input = new HashMap<>();

        String document = """         
                mutation Play($playInput2: PlayInput!) {
                    play(input: $playInput2) {
                        clientMutationId
                    }
                }
        """;

        input.put("soundZone", soundZones.get(0));
        variables.put("playInput2", input);

        graphQlClient.mutate()
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
        variables = new HashMap<>();
        input = new HashMap<>();



        input.put("source", playlistId);
        input.put("sourceTrackIndex", index);
        input.put("soundZones", soundZones);
        input.put("immediate", true);
        variables.put("input", input);

        document = """
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
