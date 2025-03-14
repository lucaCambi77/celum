import {ComponentFixture, TestBed} from '@angular/core/testing';

import {CoursesComponent} from './courses.component';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientModule} from '@angular/common/http';

describe('CoursesComponent', () => {
    let component: CoursesComponent;
    let fixture: ComponentFixture<CoursesComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            imports: [CoursesComponent, RouterTestingModule, HttpClientModule
            ]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(CoursesComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
