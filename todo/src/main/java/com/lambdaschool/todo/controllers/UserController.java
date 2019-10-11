package com.lambdaschool.todo.controllers;

import com.lambdaschool.todo.models.Todo;
import com.lambdaschool.todo.models.User;
import com.lambdaschool.todo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController
{
    @Autowired
    private UserService userService;

    //GET http://localhost:2019/users/users/
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/users",
                produces = {"application/json"})
    public ResponseEntity<?> listAllUsers()
    {
        List<User> myUsers = userService.findAll();
        return new ResponseEntity<>(myUsers,
                                    HttpStatus.OK);
    }

    // http://localhost:2019/users/user/7
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/user/{userId}",
                produces = {"application/json"})
    public ResponseEntity<?> getUserById(
            @PathVariable
                    Long userId)
    {
        User u = userService.findUserById(userId);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    // http://localhost:2019/users/user/name/cinnamon
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/user/name/{userName}",
                produces = {"application/json"})
    public ResponseEntity<?> getUserByName(
            @PathVariable
                    String userName)
    {
        User u = userService.findByName(userName);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    // http://localhost:2019/users/user/name/like/da?sort=username
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/user/name/like/{userName}",
                produces = {"application/json"})
    public ResponseEntity<?> getUserLikeName(
            @PathVariable
                    String userName)
    {
        List<User> u = userService.findByNameContaining(userName);
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    // http://localhost:2019/users/getusername
    @GetMapping(value = "/getusername",
                produces = {"application/json"})
    @ResponseBody
    public ResponseEntity<?> getCurrentUserName(Authentication authentication)
    {
        return new ResponseEntity<>(authentication.getPrincipal(),
                                    HttpStatus.OK);
    }

    // http://localhost:2019/users/getuserinfo
    @GetMapping(value = "/getuserinfo",
                produces = {"application/json"})
    public ResponseEntity<?> getCurrentUserInfo(Authentication authentication)
    {
        User u = userService.findByName(authentication.getName());
        return new ResponseEntity<>(u,
                                    HttpStatus.OK);
    }

    // http://localhost:2019/users/user
    //        {
    //            "username": "Mojo",
    //            "primaryemail": "mojo@lambdaschool.local",
    //            "password" : "Coffee123",
    //            "useremails": [
    //            {
    //                "useremail": "mojo@mymail.local"
    //            },
    //            {
    //                "useremail": "mojo@mymail.local"
    //            },
    //            {
    //                "useremail": "mojo@email.local"
    //            }
    //        ]
    //        }


//    ************************************************************************ HERE ADD USER***************************
//POST  http://localhost:2019/users/user
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping(value = "/user",
                 consumes = {"application/json"},
                 produces = {"application/json"})
    public ResponseEntity<?> addNewUser(@Valid
                                        @RequestBody
                                                User newuser) throws URISyntaxException
    {
        newuser = userService.save(newuser);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserURI = ServletUriComponentsBuilder.fromCurrentRequest()
                                                    .path("/{userid}")
                                                    .buildAndExpand(newuser.getUserid())
                                                    .toUri();
        responseHeaders.setLocation(newUserURI);

        return new ResponseEntity<>(null,
                                    responseHeaders,
                                    HttpStatus.CREATED);
    }


    // http://localhost:2019/users/user/7
    //        {
    //            "userid": 7,
    //                "username": "cinnamon",
    //                "primaryemail": "cinnamon@lambdaschool.home",
    //                "useremails": [
    //            {
    //                    "useremail": "cinnamon@mymail.local"
    //            },
    //            {
    //                    "useremail": "hops@mymail.local"
    //            },
    //            {
    //                    "useremail": "bunny@email.local"
    //            }
    //        ]
    //        }
    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUser(HttpServletRequest request,
                                        @RequestBody
                                                User updateUser,
                                        @PathVariable
                                                long id)
    {
        userService.update(updateUser,
                           id,
                           request.isUserInRole("ADMIN"));
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    // http://localhost:2019/users/user/14
//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
//    @DeleteMapping("/user/{id}")
//    public ResponseEntity<?> deleteUserById(
//            @PathVariable
//                    long id)
//    {
//        userService.delete(id);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    // http://localhost:2019/users/user/15/role/2
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/user/{userid}/role/{roleid}")
    public ResponseEntity<?> deleteUserRoleByIds(
            @PathVariable
                    long userid,
            @PathVariable
                    long roleid)
    {
        userService.deleteUserRole(userid,
                                   roleid);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/users/user/15/role/2
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/user/{userid}/role/{roleid}")
    public ResponseEntity<?> postUserRoleByIds(
            @PathVariable
                    long userid,
            @PathVariable
                    long roleid)
    {
        userService.addUserRole(userid,
                                roleid);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

//    ********************************HERE USER T0D0 *************************************************
    // GET http://localhost:2019/users/mine
    @GetMapping(value = "/mine", produces = {"application/json"})
    public ResponseEntity<?> getUserTodo()
    {
        List<User> myList =  userService.getUserTodo();

        return new ResponseEntity<>(myList ,HttpStatus.OK);
    }

// **************************************HERE USER DELETE ******************************************************
    //  DELETE http://localhost:2019/users/userid/{userid}
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/userid/{userid}")
    public ResponseEntity<?> deleteUserById(
            @PathVariable
                    long userid)
    {
        userService.delete(userid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

//    *******************************************ADD USER TODOO***************************************************
//    POST http://localhost:2019/users/t0d0/{userid} (REPLACE 0 WITH O IN URL)

    @PostMapping(value = "/todo/{userid}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<?> addTodoById(@PathVariable long userid,
                                         @RequestBody Todo Todo)
    {
        userService.addTodoById(userid, Todo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}