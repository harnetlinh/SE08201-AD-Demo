import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate


class Student (
    var name: String,
    var major: String,
    var studentId: String,
    var academicYear: Int,
    var wantJoinClub: Boolean,
    var birthYear: Int
){
    @RequiresApi(Build.VERSION_CODES.O)
    fun showInfor(): String {
        var currentYear = LocalDate.now().year;
        var age =  currentYear - birthYear;
        var stringJoinClub = "";
        if (wantJoinClub) {
            stringJoinClub = "có";
        } else {
            stringJoinClub = "không";
        }

        val message =
            "Xin chào $name, mã sinh viên $studentId, $age tuổi, " +
                    "hiện đang là sinh viên năm $academicYear ngành $major \n " +
                    "Tôi $stringJoinClub muốn tham gia Club IT";
        return message;
    }
}