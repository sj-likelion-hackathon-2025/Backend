package org.kwakmunsu.flowmate.infrastructure.s3;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.net.URL;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kwakmunsu.flowmate.global.exception.InternalServerException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.S3Utilities;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

@ExtendWith(MockitoExtension.class)
class S3ProviderTest {

    @Mock
    private S3Client s3Client;

    @Mock
    private ImageValidator imageValidator;

    @InjectMocks
    private S3Provider s3Provider;

    @BeforeEach
    void setUp() {
        // bucket과 dir 필드에 테스트 값 주입
        ReflectionTestUtils.setField(s3Provider, "bucket", "test-bucket");
        ReflectionTestUtils.setField(s3Provider, "dir", "test/");
    }

    @DisplayName("이미지 업로드 성공")
    @Test
    void uploadImage() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "test content".getBytes()
        );
        String testImgUrl = "https://bucket.s3.amazonaws.com/test-file.jpg";

        S3Utilities s3Utilities = mock(S3Utilities.class);
        URL mockUrl = mock(URL.class);

        given(s3Client.utilities()).willReturn(s3Utilities);
        given(s3Utilities.getUrl(any(GetUrlRequest.class))).willReturn(mockUrl);
        given(mockUrl.toString()).willReturn(testImgUrl);

        String result = s3Provider.uploadImage(file);

        assertThat(result).isEqualTo(testImgUrl);
        verify(s3Client).putObject(any(PutObjectRequest.class), any(RequestBody.class));
    }

    @DisplayName("이미지 업로드 실패")
    @Test
    void failUploadImageS3Exception() {
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.jpg", "image/jpeg", "test content".getBytes()
        );
        S3Exception error = (S3Exception) S3Exception.builder()
                .message("error")
                .cause(new RuntimeException("error"))
                .build();

        given(s3Client.putObject(any(PutObjectRequest.class), any(RequestBody.class)))
                .willThrow(error);

        assertThatThrownBy(() -> s3Provider.uploadImage(file))
                .isInstanceOf(InternalServerException.class);
    }

    @DisplayName("이미지 삭제 성공")
    @Test
    void deleteImages() {
        List<String> files = List.of("file1.jpg", "file2.jpg");

        s3Provider.deleteImages(files);

        verify(s3Client, times(2)).deleteObject(any(DeleteObjectRequest.class));
    }

}