package com.ttps.quecomemos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttps.quecomemos.dto.LoginUserDTO;
import com.ttps.quecomemos.dto.UpdateClientDTO;
import com.ttps.quecomemos.dto.UserRegisterDTO;
import com.ttps.quecomemos.dto.UserWithoutPasswordDTO;
import com.ttps.quecomemos.handlers.GenericExceptionHandler;
import com.ttps.quecomemos.model.Client;
import com.ttps.quecomemos.model.User;
import com.ttps.quecomemos.services.JwtService;
import com.ttps.quecomemos.services.UserService;
import com.ttps.quecomemos.util.ApiResponseDTO;
import com.ttps.quecomemos.util.UserUtils;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
@Slf4j
@Tag(name = "User Controller", description = "Operations related to users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GenericExceptionHandler exceptionHandler;

    @Autowired
    private JwtService jwtService;

    /**
     * Handles the registration of a new user.
     *
     * @param userRegisterDTO The user object to be registered.
     * @return A ResponseEntity containing the registered user and an HTTP
     * status code. - If the registration is successful, returns the registered
     * user and HTTP status 201 (Created). - If an error occurs, returns null
     * and HTTP status 500 (Internal Server Error).
     */
    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Registers a new user in the system")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "409", description = "User already exists", content = @Content),
        @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content)})
    public ResponseEntity<ApiResponseDTO<UserWithoutPasswordDTO>> registerUser(
            @RequestBody UserRegisterDTO userRegisterDTO) {
        log.info("Registering user: {}", userRegisterDTO);

        // Validate data
        UserUtils.isRegistrationDataComplete(userRegisterDTO);

        UserWithoutPasswordDTO newUser = userService
                .registerNewUser(userRegisterDTO);
        ApiResponseDTO<UserWithoutPasswordDTO> response = new ApiResponseDTO<>(
                newUser, "User registered successfully", HttpStatus.CREATED);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Logs in an existing user.
     *
     * @param loginUserDTO The user object to be logged in.
     * @return A ResponseEntity containing the logged in user and an HTTP status
     * code. - If the login is successful, returns the logged in user and HTTP
     * status 200 (OK).
     *
     */
    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Logs in an existing user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User logged in successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content),
        @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content)})
    public ResponseEntity<ApiResponseDTO<User>> login(@RequestBody LoginUserDTO loginUserDTO) {
        log.info("Logging in user: {}", loginUserDTO);

        // Validate data
        UserUtils.isLoginDataComplete(loginUserDTO);

        User loggedUser = userService.authenticateUser(loginUserDTO);

        String token = jwtService.generateToken(loggedUser.getDni());

        ApiResponseDTO<User> response = new ApiResponseDTO<>(loggedUser,
                "User logged in successfully", HttpStatus.OK);

        return ResponseEntity.ok().header("Authorization", "Bearer " + token)
                .body(response);

    }

    /**
     * Updates an existing client.
     *
     * @param dni The DNI of the client to be updated.
     * @param updateClientDTO The updated client object.
     * @return A ResponseEntity containing the updated client and an HTTP status
     * code.
     */
    @PutMapping("/update/{dni}")
    @Operation(summary = "Update Client", description = "Updates an existing client. Requires DNI of the client to be updated and the updated client object. You can update the name, email, password, and photo of the client. All fields are optional.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "401", description = "Invalid credentials", content = @Content),
        @ApiResponse(responseCode = "500", description = "Unexpected error occurred", content = @Content)})
    public ResponseEntity<ApiResponseDTO<UserWithoutPasswordDTO>> updateClient(
            @PathVariable String dni, @RequestBody UpdateClientDTO updateClientDTO) {
        log.info("Updating client: {}", updateClientDTO, " with DNI: {}", dni);

        // Validate data
        UserUtils.isUpdateDataComplete(updateClientDTO);

        // Update user
        Client updatedUser = userService.updateClient(dni, updateClientDTO);

        UserWithoutPasswordDTO userWithoutPasswordDTO = new UserWithoutPasswordDTO(
                updatedUser.getId(), updatedUser.getDni(),
                updatedUser.getName(), updatedUser.getEmail(),
                updatedUser.getRole());

        ApiResponseDTO<UserWithoutPasswordDTO> response = new ApiResponseDTO<>(
                userWithoutPasswordDTO, "Client updated successfully",
                HttpStatus.OK);
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponseDTO<Void>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e) {
        log.error("Invalid Data Input provided: {}", e.getMessage());
        ApiResponseDTO<Void> response = new ApiResponseDTO<>(null,
                e.getMessage(), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
