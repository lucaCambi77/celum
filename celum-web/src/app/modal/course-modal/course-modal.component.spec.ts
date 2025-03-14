import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CourseModalComponent} from './course-modal.component';
import {RouterTestingModule} from '@angular/router/testing';
import {NgbActiveModal, NgbModule} from '@ng-bootstrap/ng-bootstrap';

describe('CourseModalComponent', () => {
    let component: CourseModalComponent;
    let fixture: ComponentFixture<CourseModalComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [CourseModalComponent, RouterTestingModule, NgbModule
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
        fixture = TestBed.createComponent(CourseModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
