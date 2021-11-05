package com.example.zoomsoft;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertThrows;

import com.example.zoomsoft.loginandregister.User;

import org.junit.Test;

import java.util.ArrayList;

public class UserTest {


    // Login User
    private User loginMockUser() {
        return new User("asad@gmail.com", "123456", "asad70");
    }

    private User loginEmptyUser(){
        return  new User(" ", " ", " ");
    }

    @Test
    public void testGetEmail(){
        User user = loginMockUser();
        assertEquals("asad@gmail.com",user.getEmail());
        User emptyUser = loginEmptyUser();
        assertEquals(" ", emptyUser.getEmail());
    }

    @Test
   public void testSetEmail(){
        User user = loginEmptyUser();
        user.setEmail("asad10@gmail.com");
        assertEquals("asad10@gmail.com",user.getEmail());
    }


    @Test
    public void testGetPassword(){
        User user = loginMockUser();
        assertEquals("123456", user.getPassword());
        User emptyUser = loginEmptyUser();
        assertEquals(" ", emptyUser.getPassword());
    }

    @Test
    public void testSetPassword(){
        User user = loginEmptyUser();
        user.setPassword("123456789");
        assertEquals("123456789",user.getPassword());
    }

    @Test
    public void testGetUsername(){
        User user = loginMockUser();
        assertEquals("asad70",user.getUsername());
        User emptyUser = loginEmptyUser();
        assertEquals(" ", emptyUser.getUsername());
    }

    @Test
    public void testSetUsername(){
        User user = loginEmptyUser();
        user.setUsername("asad123456");
        assertEquals("asad123456",user.getUsername());
    }

    // Registration User
    public ArrayList<String> mockFollowers(){
        ArrayList<String> followers = new ArrayList<>();
        followers.add("Superman");
        followers.add("Jack");
        followers.add("Panda");
        followers.add("Shaktimaan");
        return followers;
    }

    public ArrayList<String> mockRequests(){
        ArrayList<String> requests = new ArrayList<>();
        requests.add("Chocolate");
        requests.add("Joma");
        requests.add("Spider");
        return requests;
    }


    private User mockUser() {
        return new User("asad@gmail.com", "123456", "asad70", mockFollowers(), mockRequests());
    }

    @Test
    public void testGetFollowers(){
        User user = mockUser();
        assertEquals(mockFollowers(),user.getFollowers());
    }


    @Test
    public void acceptRequests(){
        User user = mockUser();
        user.acceptRequest("Joma");
        // reduced size from 3 to 2, 3 at start; accepted one
        assertEquals(2,(user.getRequests()).size());
        // five added 4 at top,  4 at start; accepted one
        assertEquals(5,(user.getFollowers()).size());
    }

    @Test
    public void declineRequests(){
        User user = mockUser();
        user.declineRequest("Spider");
        // reduced size from 3 to 2, 3 at start; accepted one
        assertEquals(2,(user.getRequests()).size());
        // should stay the same
        assertEquals(4,(user.getFollowers()).size());
    }

    @Test
    public void declineRequestsException() {
        User user = mockUser();
        assertThrows(IllegalArgumentException.class, () -> {
            user.declineRequest("Plumber");
        });
    }


}
