import {TestBed} from '@angular/core/testing';

import {RestApiService} from './rest-api.service';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientModule} from '@angular/common/http';

describe('RestApiService', () => {
    beforeEach(() => TestBed.configureTestingModule({
        imports: [RouterTestingModule, HttpClientModule
        ]
    }));

    it('should be created', () => {
        const service: RestApiService = TestBed.get(RestApiService);
        expect(service).toBeTruthy();
    });
});
