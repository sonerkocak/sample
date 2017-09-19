import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { Sinif } from './sinif.model';
import { SinifService } from './sinif.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-sinif',
    templateUrl: './sinif.component.html'
})
export class SinifComponent implements OnInit, OnDestroy {
sinifs: Sinif[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sinifService: SinifService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sinifService.query().subscribe(
            (res: ResponseWrapper) => {
                this.sinifs = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSinifs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Sinif) {
        return item.id;
    }
    registerChangeInSinifs() {
        this.eventSubscriber = this.eventManager.subscribe('sinifListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
