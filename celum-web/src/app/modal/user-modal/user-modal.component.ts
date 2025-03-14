import {Component, OnInit} from '@angular/core';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';
import {User} from 'src/app/shared/user';
import {FormsModule} from "@angular/forms";

@Component({
    selector: 'app-user-modal',
    templateUrl: './user-modal.component.html',
    standalone: true,
    imports: [
        FormsModule
    ],
    styleUrls: ['./user-modal.component.css']
})
export class UserModalComponent implements OnInit {

    currentUser: User = new User();

    constructor(public activeModal: NgbActiveModal) {
    }

    ngOnInit() {
    }

    closeModal(currentUser: User) {
        this.activeModal.close(currentUser);
    }
}
