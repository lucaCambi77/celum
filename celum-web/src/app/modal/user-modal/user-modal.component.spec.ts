import {ComponentFixture, TestBed} from '@angular/core/testing';

import {UserModalComponent} from './user-modal.component';
import {RouterTestingModule} from '@angular/router/testing';
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

describe('UserModalComponent', () => {
    let component: UserModalComponent;
    let fixture: ComponentFixture<UserModalComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [UserModalComponent, RouterTestingModule,
            ], providers: [
                {
                    provide: NgbActiveModal,
                    useValue: {
                        close: jasmine.createSpy('close'),
                        dismiss: jasmine.createSpy('dismiss')
                    }
                }
            ]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(UserModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
