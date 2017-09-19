import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SinifComponent } from './sinif.component';
import { SinifDetailComponent } from './sinif-detail.component';
import { SinifPopupComponent } from './sinif-dialog.component';
import { SinifDeletePopupComponent } from './sinif-delete-dialog.component';

export const sinifRoute: Routes = [
    {
        path: 'sinif',
        component: SinifComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'soseApp.sinif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sinif/:id',
        component: SinifDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'soseApp.sinif.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sinifPopupRoute: Routes = [
    {
        path: 'sinif-new',
        component: SinifPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'soseApp.sinif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sinif/:id/edit',
        component: SinifPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'soseApp.sinif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sinif/:id/delete',
        component: SinifDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'soseApp.sinif.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
