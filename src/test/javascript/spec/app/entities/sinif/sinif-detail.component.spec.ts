/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { SoseTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SinifDetailComponent } from '../../../../../../main/webapp/app/entities/sinif/sinif-detail.component';
import { SinifService } from '../../../../../../main/webapp/app/entities/sinif/sinif.service';
import { Sinif } from '../../../../../../main/webapp/app/entities/sinif/sinif.model';

describe('Component Tests', () => {

    describe('Sinif Management Detail Component', () => {
        let comp: SinifDetailComponent;
        let fixture: ComponentFixture<SinifDetailComponent>;
        let service: SinifService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SoseTestModule],
                declarations: [SinifDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SinifService,
                    JhiEventManager
                ]
            }).overrideTemplate(SinifDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SinifDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SinifService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Sinif(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.sinif).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
