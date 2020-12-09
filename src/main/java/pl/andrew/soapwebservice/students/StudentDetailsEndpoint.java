package pl.andrew.soapwebservice.students;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Optional;

@Endpoint
public class StudentDetailsEndpoint {
    private static final String NAMESPACE_URI = "http://andrew.pl/soapwebservice/students";

    private final StudentRepository studentRepository;

    @Autowired
    public StudentDetailsEndpoint(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetStudentDetailsRequest")
    @ResponsePayload
    public GetStudentDetailsResponse getStudent(@RequestPayload GetStudentDetailsRequest request) {
        GetStudentDetailsResponse response = new GetStudentDetailsResponse();
        StudentDetails studentDetails = new StudentDetails();

        Optional<Student> optionalStudent = studentRepository.findById(request.getId());
        if (optionalStudent.isPresent()) {
            studentDetails.setId(optionalStudent.get().getId());
            studentDetails.setName(optionalStudent.get().getName());
            studentDetails.setPassportNumber(optionalStudent.get().getPassportNumber());
        }

//        studentDetails.setId(request.getId());
//        studentDetails.setName("Andrzej");
//        studentDetails.setPassportNumber("E1234567");

        response.setStudentDetails(studentDetails);

        return response;
    }
}
