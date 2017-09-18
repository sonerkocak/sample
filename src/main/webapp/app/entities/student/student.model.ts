import { BaseEntity } from './../../shared';

export const enum GenderType {
    'MALE',
    'FEMALE'
}

export class Student implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public number?: number,
        public gender?: GenderType,
    ) {
    }
}
