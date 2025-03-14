import {Component, OnInit} from '@angular/core';
import {Course} from 'src/app/shared/course';
import {NgbActiveModal, NgbCalendar, NgbDateParserFormatter, NgbDateStruct, NgbInputDatepicker} from '@ng-bootstrap/ng-bootstrap';
import {FormsModule} from '@angular/forms';

@Component({
    selector: 'app-course-modal',
    templateUrl: './course-modal.component.html',
    standalone: true,
    imports: [
        FormsModule,
        NgbInputDatepicker
    ],
    styleUrls: ['./course-modal.component.css']
})
export class CourseModalComponent implements OnInit {

    currentCourse: Course = new Course();
    courseDate: NgbDateStruct;

    constructor(public activeModal: NgbActiveModal, private calendar: NgbCalendar
        , private parserFormatter: NgbDateParserFormatter) {
    }

    ngOnInit() {
    }

    closeModal(currentCourse: Course) {
        currentCourse.startDate = new Date(this.courseDate.year, this.courseDate.month, this.courseDate.day);
        this.activeModal.close(currentCourse);
    }
}
