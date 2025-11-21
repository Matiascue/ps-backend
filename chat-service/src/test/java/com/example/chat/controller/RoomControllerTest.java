package com.example.chat.controller;

import com.example.chat.Dto.info.RoomInfoDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createOrGetRoomNewCode200() throws Exception {
        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.post("/api/room/create")
                .contentType(MediaType.APPLICATION_JSON).content("{\n"+
                        " \"user1Id\": \"2\",\n"+
                        "\"user2Id\": \"3\"\n"+
                        "}"))
                .andDo(print())
                . andExpect(status().isOk())
                .andReturn();
        String responseContent=result.getResponse().getContentAsString();
        ObjectMapper objectMapper=new ObjectMapper();
        RoomInfoDto roomInfoDto=objectMapper.readValue(responseContent,RoomInfoDto.class);
        Assertions.assertEquals("2_3",roomInfoDto.getRoomId());
        Assertions.assertEquals(2,roomInfoDto.getUser1Id());
    }
    @Test
    void createOrGetRoomExistRoomCode200() throws Exception {
        MvcResult result =mockMvc.perform(MockMvcRequestBuilders.post("/api/room/create")
                        .contentType(MediaType.APPLICATION_JSON).content("{\n"+
                                " \"user1Id\": \"1\",\n"+
                                "\"user2Id\": \"2\"\n"+
                                "}"))
                .andDo(print())
                . andExpect(status().isOk())
                .andReturn();
        String responseContent=result.getResponse().getContentAsString();
        ObjectMapper objectMapper=new ObjectMapper();
        RoomInfoDto roomInfoDto=objectMapper.readValue(responseContent,RoomInfoDto.class);
        Assertions.assertEquals("1_2",roomInfoDto.getRoomId());
        Assertions.assertEquals(1,roomInfoDto.getUser1Id());
        Assertions.assertEquals(2,roomInfoDto.getUser2Id());
        Assertions.assertEquals("Hola desde H2!",roomInfoDto.getMessagesUser1().get(0).getContent());
    }

    @Test
    void getHistorialCode200() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/room/{roomId}/messages","1_2")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

    }
    @Test
    void getRoomsCode200WithUserId1() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/room/rooms/{userId}","1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
    @Test
    void getRoomsCode200WithUserId2() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/room/rooms/{userId}","2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers
                        .content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }
}